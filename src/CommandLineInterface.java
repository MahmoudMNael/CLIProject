import Repositories.CommandsRepository;
import jdk.jshell.spi.ExecutionControlProvider;

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
        _workingDirectory = new File(System.getProperty("user.home"));
        _commandsRepository = new CommandsRepository();
        _inputStream = new ArrayList<>();
    }

    public void run() throws IOException, Exception {

        while (true) {
            try {
                System.out.println("\u001B[32m" + System.getProperty("user.name"));
                if (_workingDirectory.getCanonicalPath().equals(System.getProperty("user.home"))) {
                    System.out.print("\u001B[34m" + "~" + "\u001B[0m" + "$ ");
                } else {
                    System.out.print("\u001B[34m" + _workingDirectory.getCanonicalPath() + "\u001B[0m" + "$ ");
                }
                getInput();
                handleInput();
                System.out.println("");
            } catch (Exception e) {
                System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                System.out.println("");
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
        Boolean isPiped = false;
        while (!_inputStream.isEmpty()) {
            ArrayList<String> flags = new ArrayList<>();
            ArrayList<String> inputs = new ArrayList<>();
            switch (_inputStream.get(0)) {
                case "pwd":
                    _inputStream.removeFirst();
                    extractCommandHelpers(flags, inputs, isPiped);
                    _output = _commandsRepository.pwd(_workingDirectory);
                    isPiped = false;
                    break;
                case "mkdir":
                    _inputStream.removeFirst();
                    extractCommandHelpers(flags, inputs, isPiped);
                    _commandsRepository.mkdir(_workingDirectory, inputs);
                    isPiped = false;
                    break;
                case "ls":
                    _inputStream.removeFirst();
                    extractCommandHelpers(flags, inputs, isPiped);
                    _output = _commandsRepository.ls(_workingDirectory, flags);
                    isPiped = false;
                    break;
                case "|":
                    _inputStream.removeFirst();
                    isPiped = true;
                    break;
                case "exit":
                    exit(0);
            }
        }
        if (isPiped == false) {
            printOutput();
        }
    }

    private void printOutput() {
        System.out.println(_output);
    }

    private void extractCommandHelpers(ArrayList<String> flags, ArrayList<String> inputs, Boolean isPiped) {
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
        if (isPiped) {
            if (_output.contains("\n")) {
                List<String> outputList = Arrays.stream(_output.split("\n")).toList();
                inputs.addAll(0, outputList);
            } else {
                inputs.addFirst(_output);
            }
        }
    }
}
