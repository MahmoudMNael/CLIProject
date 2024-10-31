package Tests;

import Repositories.CommandsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CommandsRepositoryTest {
    private CommandsRepository _commandRepository;

    @Test
    @DisplayName("it succeeds when calling the function")
    void pwd() throws IOException {
        _commandRepository = new CommandsRepository();
        assertInstanceOf(String.class, _commandRepository.pwd(new File(".")));
    }

    @Test
    @DisplayName("it succeeds when calling the function")
    void touch() throws IOException {
        _commandRepository = new CommandsRepository();
        assertDoesNotThrow(() -> _commandRepository.touch(new File("."), new ArrayList<>(Arrays.asList("file1"))));
    }

    @Test
    @DisplayName("it succeeds when calling the function")
    void catSuccess() throws IOException, Exception {

        _commandRepository = new CommandsRepository();
        assertInstanceOf(String.class, _commandRepository.cat(new File("."), new ArrayList<>(Arrays.asList("file1"))));
    }

    @Test
    @DisplayName("it succeeds when calling the function")
    void catSuccessCreateFile() throws IOException, Exception {

        _commandRepository = new CommandsRepository();
        assertDoesNotThrow(() -> _commandRepository.cat(new File("."), new ArrayList<>(Arrays.asList("file1",">","file2" ))));
    }

    @Test
    @DisplayName("it fails when calling the function")
    void catFailure() throws IOException, Exception {

        _commandRepository = new CommandsRepository();
        assertThrows(Exception.class, () -> _commandRepository.cat(new File("."), new ArrayList<>(Arrays.asList("file4"))));
    }


}
