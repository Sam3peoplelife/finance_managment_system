package javasave;

import java.io.IOException;
import java.util.List;

public class Ocamlsave {
    private static String mlByte = "./crud/src/crud_csv.byte";

    /**
     * Saves category, budget, and transaction data using an OCaml program.
     *
     * @param categoriesData    list of category data to save
     * @param budgetsData       list of budget data to save
     * @param transactionsData list of transaction data to save
     * @param categoriesPath    file path to save category data
     * @param budgetsPath       file path to save budget data
     * @param transactionsPath file path to save transaction data
     */
    public static void saveDataOCaml(List<String> categoriesData, List<String> budgetsData, List<String> transactionsData,
                                     String categoriesPath, String budgetsPath, String transactionsPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("ocamlrun", mlByte, "save",
                    categoriesPath, budgetsPath, transactionsPath,
                    String.join(",", categoriesData), String.join(",", budgetsData), String.join(",", transactionsData));
            Process process = pb.start();

            // Check for errors
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Data saved successfully using OCaml.");
            } else {
                System.out.println("Failed to save data using OCaml.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


