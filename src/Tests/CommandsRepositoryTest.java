package Tests;

import Repositories.CommandsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CommandsRepositoryTest {
    private CommandsRepository _commandRepository = new CommandsRepository();

    @Test
    @DisplayName("pwd succeeds when calling the function")
    void pwdSuccess() throws IOException {
        assertInstanceOf(String.class, _commandRepository.pwd(new File(".")));
    }

    @Test
    @DisplayName("cd subfolder one time")
    void cdSubfolder() throws Exception{
        _commandRepository = new CommandsRepository();
        ArrayList<String> array = new ArrayList<String>();
        array.add("src/Tests");
        File dir = new File("D:\\Programming\\CLIProject");
        System.out.println(_commandRepository.cd(dir,array));
        assertEquals("D:\\Programming\\CLIProject/src/Tests", _commandRepository.cd(dir,array));
    }

    @Test
    @DisplayName("cd spaced folder")
    void cdSpacedDirectory() throws Exception{
        _commandRepository = new CommandsRepository();
        ArrayList<String> array = new ArrayList<String>();
        array.add("Space Test");
        File dir = new File("D:\\Programming\\CLIProject");
        System.out.println(_commandRepository.cd(dir,array));
        assertEquals("D:\\Programming\\CLIProject/Space Test", _commandRepository.cd(dir,array));
    }

    @Test
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
    // Test will not succeed twice in a row.
    // You should delete the created directory or run the rmdir test before running it again
    // Or run the whole test class bundle
    @Test
    @DisplayName("mkdir succeeds when given valid inputs")
    void mkdirSuccess() throws Exception {
        assertDoesNotThrow(() -> _commandRepository.mkdir(new File("."), new ArrayList<>(Arrays.asList("./testdirectory"))));
    }

    @Test
    @DisplayName("mkdir fails when not given any input")
    void mkdirFailureEmptyInput() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new File("."), new ArrayList<>()));
        assertEquals("mkdir: Missing operand!", exception.getMessage());
    }

    @Test
    @DisplayName("mkdir fails when given an already existing directory")
    void mkdirFailureExistingDirectory() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new File("."), new ArrayList<>(Arrays.asList("src"))));
        assertEquals("mkdir: Directory already exists!", exception.getMessage());
    }

    @Test
    @DisplayName("mkdir fails when given a non valid path")
    void mkdirFailureNoDirectory() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.mkdir(new File("."), new ArrayList<>(Arrays.asList("blahblah/testdir"))));
        assertEquals("mkdir: No such file or directory!", exception.getMessage());
    }

    @Test
    @DisplayName("rmdir fails when trying to remove dir with subfolders")
    void rmdirFailureDirWithSubFolders() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.rmdir(new File("."), new ArrayList<>(Arrays.asList("FullFolder"))));
        assertEquals("This directory has children so it can not be deleted", exception.getMessage());
    }

    @Test
    @DisplayName("rmdir fails when trying to remove file")
    void rmdirFailureFile() throws Exception {
        _commandRepository = new CommandsRepository();
        Exception exception = assertThrows(Exception.class, () -> _commandRepository.rmdir(new File("."), new ArrayList<>(Arrays.asList("README.md"))));
        assertEquals("It is not directory to be removed", exception.getMessage());
    }

    @DisplayName("ls succeeds when given no flags")
    void lsSuccessNoFlags() throws Exception {
        assertInstanceOf(String.class, _commandRepository.ls(new File("."), new ArrayList<>()));
    }

    @Test
    @DisplayName("ls succeeds when given -a flag")
    void lsSuccessAFlag() throws Exception {
        String list = _commandRepository.ls(new File("."), new ArrayList<>(Arrays.asList("-a")));
        assertInstanceOf(String.class, list);
        assertTrue(list.contains("./"));
    }

    @Test
    @DisplayName("ls succeeds when given -r flag")
    void lsSuccessRFlag() throws Exception {
        assertInstanceOf(String.class, _commandRepository.ls(new File("."), new ArrayList<>(Arrays.asList("-r"))));
    }

    @Test
    @DisplayName("ls succeeds when given -a -r flags")
    void lsSuccessARFlags() throws Exception {
        String list = _commandRepository.ls(new File("."), new ArrayList<>(Arrays.asList("-a", "-r")));
        assertInstanceOf(String.class, list);
        assertTrue(list.contains("./"));
    }
}