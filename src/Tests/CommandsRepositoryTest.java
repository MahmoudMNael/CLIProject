package Tests;

import Repositories.CommandsRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

// PLEASE RUN THE TEST AS A BUNDLE
// SOME TESTS LIKE rmdir AND mkdir, etc... WILL FAIL IF RAN TWICE
// SO AFTER WE mkdir WE rmdir IN THE BUNDLE TEST
// PLEASE RUN WHOLE CLASS TEST

class CommandsRepositoryTest {
    private CommandsRepository _commandRepository = new CommandsRepository();

    @Test
    @Order(1)
    @DisplayName("pwd succeeds when calling the function")
    void pwdSuccess() throws IOException {
        assertInstanceOf(String.class, _commandRepository.pwd(new File(".")));
    }

    @Test
    @Order(2)
    @DisplayName("mkdir succeeds when given valid inputs")
    void mkdirSuccess() throws Exception {
        assertDoesNotThrow(() -> _commandRepository.mkdir(new File("."), new ArrayList<>(Arrays.asList("./testdirectory"))));
    }

    @Test
    @Order(3)
    @DisplayName("mkdir fails when not given any input")
    void mkdirFailureEmptyInput() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new File("."), new ArrayList<>()));
        assertEquals("mkdir: Missing operand!", exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("mkdir fails when given an already existing directory")
    void mkdirFailureExistingDirectory() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new File("."), new ArrayList<>(Arrays.asList("src"))));
        assertEquals("mkdir: Directory already exists!", exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("mkdir fails when given a non valid path")
    void mkdirFailureNoDirectory() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new File("."), new ArrayList<>(Arrays.asList("blahblah/testdir"))));
        assertEquals("mkdir: No such file or directory!", exception.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("it succeeds when calling the function")
    void touch() throws IOException {
        _commandRepository = new CommandsRepository();
        assertDoesNotThrow(() -> _commandRepository.touch(new File("."), new ArrayList<>(Arrays.asList("file1"))));
    }

    @Test
    @Order(7)
    @DisplayName("it succeeds when calling the function")
    void catSuccess() throws IOException, Exception {
        _commandRepository = new CommandsRepository();
        assertInstanceOf(String.class, _commandRepository.cat(new File("."), new ArrayList<>(Arrays.asList("file1"))));
    }

    @Test
    @Order(8)
    @DisplayName("it succeeds when calling the function")
    void catSuccessCreateFile() throws IOException, Exception {
        _commandRepository = new CommandsRepository();
        assertDoesNotThrow(() -> _commandRepository.cat(new File("."), new ArrayList<>(Arrays.asList("file1",">","file2" ))));
    }

    @Test
    @Order(9)
    @DisplayName("it fails when calling the function")
    void catFailure() throws IOException, Exception {
        _commandRepository = new CommandsRepository();
        assertThrows(Exception.class, () -> _commandRepository.cat(new File("."), new ArrayList<>(Arrays.asList("file4"))));
    }

    @Test
    @Order(10)
    @DisplayName("cd spaced folder")
    void cdSpacedDirectory() throws Exception{
        _commandRepository = new CommandsRepository();
        ArrayList<String> array = new ArrayList<String>();
        array.add(".");
        File dir = new File(".");
        assertEquals("./.", _commandRepository.cd(dir,array));
    }

    @Test
    @Order(11)
    @DisplayName("cd not found")
    void cdPathNotFound() throws Exception{
        _commandRepository = new CommandsRepository();
        ArrayList<String> array = new ArrayList<String>();
        array.add("abcd");
        File dir = new File("D:\\Programming\\CLIProject");
        boolean thrown = false;
        try {
            _commandRepository.cd(dir,array);
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    @Order(12)
    @DisplayName("mv fails when given file is not found")
    void mvFailureNotFound() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mv(new File("."), new ArrayList<>(Arrays.asList("MovedFiless","src/MovedFile"))));
        assertEquals("File is not found", exception.getMessage());
    }

    @Test
    @Order(13)
    @DisplayName("mv fails when the given inputs is not enough")
    void mvFailureInputInvalid() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mv(new File("."), new ArrayList<>(Arrays.asList("MovedFile"))));
        assertEquals("Input is not valid, Please enter the file name and the path you want to move it to", exception.getMessage());
    }

    @Test
    @Order(14)
    @DisplayName("rmdir succeeds when given valid input!")
    void rmdirSuccess() throws Exception {
        _commandRepository = new CommandsRepository();
        assertDoesNotThrow(() -> _commandRepository.rmdir(new File("."), new ArrayList<>(Arrays.asList("testdirectory"))));
    }

    @Test
    @Order(15)
    @DisplayName("rmdir fails when trying to remove dir with subfolders")
    void rmdirFailureDirWithSubFolders() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.rmdir(new File("."), new ArrayList<>(Arrays.asList("src"))));
        assertEquals("This directory has children so it can not be deleted", exception.getMessage());
    }

    @Test
    @Order(16)
    @DisplayName("rmdir fails when trying to remove file")
    void rmdirFailureFile() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.rmdir(new File("."), new ArrayList<>(Arrays.asList("README.md"))));
        assertEquals("It is not directory to be removed", exception.getMessage());
    }

    @Test
    @Order(17)
    @DisplayName("ls succeeds when given no flags")
    void lsSuccessNoFlags() throws Exception {
        assertInstanceOf(String.class, _commandRepository.ls(new File("."), new ArrayList<>()));
    }

    @Test
    @Order(18)
    @DisplayName("ls succeeds when given -a flag")
    void lsSuccessAFlag() throws Exception {
        String list = _commandRepository.ls(new File("."), new ArrayList<>(Arrays.asList("-a")));
        assertInstanceOf(String.class, list);
        assertTrue(list.contains("./"));
    }

    @Test
    @Order(19)
    @DisplayName("ls succeeds when given -r flag")
    void lsSuccessRFlag() throws Exception {
        assertInstanceOf(String.class, _commandRepository.ls(new File("."), new ArrayList<>(Arrays.asList("-r"))));
    }

    @Test
    @Order(20)
    @DisplayName("ls succeeds when given -a -r flags")
    void lsSuccessARFlags() throws Exception {
        String list = _commandRepository.ls(new File("."), new ArrayList<>(Arrays.asList("-a", "-r")));
        assertInstanceOf(String.class, list);
        assertTrue(list.contains("./"));
    }

    @Test
    @Order(21)
    @DisplayName("rm succeeds when given valid input!")
    void rm() throws Exception {
        _commandRepository = new CommandsRepository();
        File created_file = new File("file1");
        File created_file2 = new File("file2");
        ArrayList<String> array = new ArrayList<String>();
        array.add("file1");
        _commandRepository.rm(new File("."), array);
        ArrayList<String> array2 = new ArrayList<String>();
        array2.add("file2");
        _commandRepository.rm(new File("."), array2);
        boolean checkIfExists = false;
        if (created_file.exists() && created_file2.exists()){
            checkIfExists = true;
        }
        assertFalse(checkIfExists);
    }
}