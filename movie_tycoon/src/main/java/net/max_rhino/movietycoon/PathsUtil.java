package net.max_rhino.movietycoon;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class PathsUtil {
    public static Path getPath(String resource) {
        try {
            return Paths.get(Objects.requireNonNull(Main.class.getResource(resource)).toURI()).toAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Path font(String resource) {
        if (!resource.startsWith("/")) {
            resource = "/" + resource;
        }
        if (!resource.contains(".")) {
            resource = resource + ".ttf";
        }
        return getPath("/assets/fonts" + resource);
    }

    public static Path image(String resource) {
        if (!resource.startsWith("/")) {
            resource = "/" + resource;
        }
        if (!resource.contains(".")) {
            resource = resource + ".png";
        }
        return getPath("/assets/images" + resource);
    }
}
