# ProjetInfo731

This project demonstrates a simple **Map/Reduce** implementation for counting words in a text file. It splits the text into blocks, processes each block in parallel, then aggregates the results.

## Prerequisites

- Java 8 or higher

## Compilation

Run the following command from the repository root:

```bash
mvn compile
```

## Execution

Start the application with:

```bash
mvn exec:java
```

By default the program reads `lesmiserables.txt` from `src/main/resources` and prints the dictionary of counted words.

### Sample output

```
[{=375130, frowning=70, CochepaiUe=70, coupable=280, ...}
Execution time: XXXX milliseconds
```

## License

This project is distributed under the MIT License. See the [LICENSE](LICENSE) file for details.
