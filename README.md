Directory Structure:
```
finance-management-system/
├── src/
│   ├── main/
│   │   ├── Main.java
│   ├── menu/
│   │   ├── Menu.java
│   │   ├── MenuTest.java
│   ├── domain/
│   │   ├── Transaction.java
│   │   ├── Category.java
│   │   ├── Budget.java
│   │   ├── User.java
│   │   ├── SimpleUser.java
│   │   ├── VIPUser.java
│   ├── javasave/
│   │   ├── Javasave.java
│   │   ├── Ocamlsave.java
├── crud/
│   ├── data/
│   │   ├── transactions.csv
│   │   ├── budgets.csv
│   │   ├── categories.csv
│   ├── javadata/
│   │   ├── transactions.csv
│   │   ├── budgets.csv
│   │   ├── categories.csv
│   ├── src/
│   │   ├── crud_csv.ml
```

## CLI Commands

Compile Ocaml
```
ocamlc -o ./crud/src/crud_csv.byte -I +str str.cma ./crud/src/crud_csv.ml
```
Compile Java files
```
javac -d bin -cp src src/main/Main.java src/menu/Menu.java src/domain/*.java src/javasave/*.java
```
Run project
```
java -cp bin main.Main
```
Run Tests
```
java -cp bin menu.MenuTest
```
