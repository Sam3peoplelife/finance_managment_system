let write_csv filename data =
  let oc = open_out filename in
  List.iter (fun line -> output_string oc (line ^ "\n")) data;
  close_out oc

let read_csv filename =
  let ic = open_in filename in
  let rec read_lines acc =
    try
      let line = input_line ic in
      read_lines (line :: acc)
    with End_of_file ->
      close_in ic;
      List.rev acc
  in
  read_lines []

let save_data categories_file budgets_file transactions_file categories budgets transactions =
  let categories_to_save =
    match categories with
    | hd :: tl when hd = "Root" -> tl
    | _ -> categories
  in
  write_csv categories_file [String.concat "," categories_to_save];
  write_csv budgets_file [String.concat "," budgets];
  write_csv transactions_file [String.concat "," transactions];
  Printf.printf "Data saved to %s, %s, %s\n" categories_file budgets_file transactions_file


let load_data categories_file budgets_file transactions_file =
  let categories = read_csv categories_file in
  let budgets = read_csv budgets_file in
  let transactions = read_csv transactions_file in
  List.iter (fun line -> Printf.printf "Category: %s\n" line) categories;
  List.iter (fun line -> Printf.printf "Budget: %s\n" line) budgets;
  List.iter (fun line -> Printf.printf "Transaction: %s\n" line) transactions;
  Printf.printf "Data loaded from %s, %s, %s\n" categories_file budgets_file transactions_file

let () =
  Array.iteri (fun i arg -> Printf.printf "Arg[%d]: %s\n" i arg) Sys.argv;
  match Sys.argv with
  | [| _; "save"; categories_file; budgets_file; transactions_file; categories_data; budgets_data; transactions_data |] ->
      let categories = Str.split (Str.regexp ",") categories_data in
      let budgets = Str.split (Str.regexp ",") budgets_data in
      let transactions = Str.split (Str.regexp ",") transactions_data in
      save_data categories_file budgets_file transactions_file categories budgets transactions;
      exit 0
  | [| _; "load"; categories_file; budgets_file; transactions_file |] ->
      load_data categories_file budgets_file transactions_file;
      exit 0
  | _ ->
      Printf.printf "Invalid arguments\n";
      exit 1
