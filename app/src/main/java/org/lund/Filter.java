/* Copyright (c) 2021, Idriss Riouak <idriss.riouak@cs.lth.se>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.lund;
import CLI.src.Table;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Given a table and filters prints the same table but filtered.
 */
public class Filter {

  private static String filename;
  // public static ArrayList<String> userFilter = new ArrayList<String>();
  public static ArrayList<HashMap<String, String>> userFilter =
      new ArrayList<HashMap<String, String>>();

  public static Table table;

  private String[] setEnv(String[] args) throws FileNotFoundException {
    if (args.length < 1) {
      System.err.println("You must specify a source file on the command line!");
      printOptionsUsage();
    }

    ArrayList<String> FEOptions = new ArrayList<>();

    filename = args[0];
    for (int i = 0; i < args.length; ++i) {
      String opt = args[i];
      if (opt.equals("-help")) {
      } else if (opt.startsWith("-filterby=")) { // Getting all the user filters
        opt = opt.substring(10, opt.length());
        String[] filters = opt.split(",");
        for (String s : filters) {
          String key = s.substring(0, s.indexOf("{"));
          String value = s.substring(s.indexOf("{") + 1, s.indexOf("}"));
          HashMap<String, String> newHashMap = new HashMap<String, String>();
          newHashMap.put(key, value);
          userFilter.add(newHashMap);
        }
        continue;
      }
      System.err.println("Error: unrecognized option: " + opt);
      printOptionsUsage();
    }
    return FEOptions.toArray(new String[FEOptions.size()]);
  }

  public static void main(String args[]) throws FileNotFoundException {
    Filter filter = new Filter();
    filter.setEnv(args);
    table = new Table(System.in); // Reading and creting  a table from stdin
    for (HashMap<String, String> map : userFilter) {
      for (Map.Entry<String, String> entry : map.entrySet()) {
        String column = entry.getKey();
        String value = entry.getValue();
        table.filterBy(column, value); // Filtering the table
      }
    }
    System.out.println(table); // Displaing the table on stdout
  }

  public static String name() { return "Filter"; }
  public static String version() { return "ProjectCourse2021"; }

  void printOptionsUsage() {
    System.out.println(name() + " - Version:" + version());
    System.out.println("Authors: Idriss Riouak & Momina Rizwan");
    System.out.println("\n");
    System.out.println(
        "./filter -filterby=column_name1{substitution1},column_name2{substitution2}");
    System.out.println("\n");
    System.out.println(
        "if 'column_name' does not exists an error is printed on stderr.");
    System.exit(1);
  }
}
