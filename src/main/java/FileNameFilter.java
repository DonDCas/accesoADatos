import java.io.File;

public interface FileNameFilter {
    boolean accept(File directory, String fileName);
}
