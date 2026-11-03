# MyBudget – Household Account Book

Software System Design project (Topic 4: Household Account Book Program).  
Author: Maria Nolte De Sena (202630405) – Chonnam National University, Dept. Artificial Intelligence.

MyBudget is a Java Swing desktop application to register income and expense
transactions, edit and delete them, search and filter them by month, category
and type, see the total income / total expenses / balance, and save the data
to a file (`transactions.dat`).

**Status:** beta 0.9 (Checkpoint 4).

## Run

Requires Java 8 or newer.

```
java -jar ssd_ip_cp4_2026f_202630405_app.jar
```

The data file `transactions.dat` is created in the folder where the program is run.

## Build from source

```
javac -d bin src/mybudget/*.java
jar cfe MyBudget.jar mybudget.MyBudgetApp -C bin .
javadoc -d api_manual -sourcepath src -subpackages mybudget -author -version
```

In IntelliJ IDEA: *File > Open* and select this folder.

## Structure

All the classes are in the package `mybudget` (folder `src/mybudget`):

- `MyBudgetApp` – starts the program (main)
- `AccountBook`, `Transaction`, `FinancialSummary` – the data and the functions
- `MainWindow`, `TransactionListWindow`, `TransactionDialog`, `SummaryDialog` – the windows

The class diagram is in `docs/class_diagram.png`.
