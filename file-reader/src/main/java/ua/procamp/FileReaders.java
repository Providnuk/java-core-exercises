package ua.procamp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

/**
 * {@link FileReaders} privides an API that allow to read whole file into a {@link String} by file name.
 */
public class FileReaders {

    /**
     * Returns a {@link String} that contains whole text from the file specified by name.
     *
     * @param fileName a name of a text file
     * @return string that holds whole file content
     */
    public static String readWholeFile(String fileName) {
        Objects.requireNonNull(fileName);
        try {
            return Files.lines(Path
                    .of(FileReaders.class.getClassLoader().getResource(fileName).toURI()))
                    .collect(joining("\n"));
        } catch (IOException | URISyntaxException e) {
            return "";
        }
    }
}
