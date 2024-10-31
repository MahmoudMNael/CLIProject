package Tests;

import Repositories.CommandsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CommandsRepositoryTest {
    private CommandsRepository _commandRepository;

    @Test
    @DisplayName("it succeeds when calling the function")
    void pwdSuccess() throws IOException {
        _commandRepository = new CommandsRepository();
        assertInstanceOf(String.class, _commandRepository.pwd(new File(".")));
    }
}