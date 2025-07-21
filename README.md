# ProjetInfo731

This project demonstrates a simple **Map/Reduce** implementation for counting words in a text file. It splits the text into blocks, processes each block in parallel, then aggregates the results.

## Architecture overview

The program follows a classic Map/Reduce pattern implemented with Java threads:

1. **Splitting** – The `Splitter` class reads an input file and divides it
   into a configurable number of text chunks.
2. **Mapping** – Each chunk is processed by a `MapTask` instance. The
   `CoordinateurNode` submits tasks to a thread pool, each computing a map of
   word counts in parallel. The mapper normalizes words to lower case and
   ignores empty tokens so counts are consistent regardless of input case or
   punctuation.
3. **Reducing** – Once mapping is complete, the coordinator assigns the partial
   maps to `ReduceTask` instances submitted to the same pool. Each
   reduce task aggregates its input map.
4. **Aggregation** – The coordinator finally combines all reduced maps into a
   single dictionary containing the total counts.

Tasks are executed using a fixed-size `ExecutorService` which manages a pool of
worker threads. This approach scales better than manually creating threads and
ensures that executors are properly shut down once all jobs finish.

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

## Tests

JUnit tests validate the `MapTask`, `ReduceTask` and `Splitter` classes.
Run them with:

```bash
mvn test
```

## License

This project is distributed under the MIT License. See the [LICENSE](LICENSE) file for details.
