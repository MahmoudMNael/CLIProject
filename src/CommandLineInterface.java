import Repositories.CommandsRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class CommandLineInterface {
    static private ArrayList<String> keywords = new ArrayList<>(Arrays.asList("pwd", "cd", "ls", "mkdir", "rmdir", "touch", "mv", "rm", "cat", "|"));
    private File _workingDirectory;
    private CommandsRepository _commandsRepository;
    private ArrayList<String> _inputStream;
    private String _output;

    CommandLineInterface() {
        _workingDirectory = new File("./src");
        _commandsRepository = new CommandsRepository();
        _inputStream = new ArrayList<>();
    }

    public void run() throws IOException, Exception {
        while (true) {
            try {
                System.out.println("\u001B[32m" + _workingDirectory.getCanonicalPath());
                System.out.print("$ " + "\u001B[0m");
                getInput();
                handleInput();
                System.out.println("");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void getInput() {
        Scanner scanner = new Scanner(System.in);

        if (!_inputStream.isEmpty()){
            _inputStream.clear();
        }
        _inputStream.addAll(List.of(scanner.nextLine().split(" +")));
    }

    private void handleInput() throws IOException, Exception {
        while (!_inputStream.isEmpty()) {
            ArrayList<String> flags = new ArrayList<>();
            ArrayList<String> inputs = new ArrayList<>();
            switch (_inputStream.get(0)) {
                case "pwd":
                    _inputStream.removeFirst();
                    extractCommandHelpers(flags, inputs);
                    _output = _commandsRepository.pwd(_workingDirectory);
                    printOutput();
                    break;
                case "touch":
                    _inputStream.removeFirst();
                    extractCommandHelpers(flags, inputs);
                    _commandsRepository.touch(_workingDirectory, inputs);
                    break;
                case "cat":
                    _inputStream.removeFirst();
                    extractCommandHelpers(flags, inputs);
                    _output = _commandsRepository.cat(_workingDirectory, inputs);
                    printOutput();
                    break;
                case "exit":
                    exit(0);
            }
        }
    }

    private void printOutput() {
        System.out.println(_output);
    }

    private void extractCommandHelpers(ArrayList<String> flags, ArrayList<String> inputs) {
        outer:
        while (!_inputStream.isEmpty()) {
            if (_inputStream.get(0).charAt(0) == '-') {
                flags.add(_inputStream.get(0));
                _inputStream.removeFirst();
            } else if (keywords.contains(_inputStream.get(0))) {
                return;
            } else {
                if (_inputStream.get(0).charAt(0) == '"'){
                    String tempInput = "";
                    while (true) {
                        tempInput += _inputStream.get(0) + " ";
                        if (_inputStream.get(0).charAt(_inputStream.get(0).length() - 1) == '"') {
                            _inputStream.removeFirst();
                            inputs.add(tempInput.split("\"")[1]);
                            continue outer;
                        }
                        _inputStream.removeFirst();
                    }
                }
                inputs.add(_inputStream.get(0));
                _inputStream.removeFirst();
            }
        }
    }
}
