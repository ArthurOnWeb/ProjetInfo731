# ProjetInfo731

This Java project demonstrates a simple MapReduce-style word count. It splits a text file into chunks, maps occurrences of each word, and reduces the results using multiple threads.

## Usage

Compile and run the `Main` class. The program reads `lesmiserables.txt` from the `src/Ressources` directory, processes the text, and outputs the word counts.

The full `lesmiserables.txt` file is quite large (~74&nbsp;MB). If you don't need the entire text you can use the lighter `lesmiserablespetit.txt` sample or store the full file outside of the repository and adjust the path accordingly.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
