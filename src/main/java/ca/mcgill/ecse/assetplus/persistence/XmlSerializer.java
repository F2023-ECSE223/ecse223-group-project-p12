package ca.mcgill.ecse.assetplus.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.AbstractDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * Serializes model elements to/from XML.
 */
public class XmlSerializer {

  private XStream xStream;

  public XmlSerializer(String packageName) {
    this(packageName, new XppDriver());
    setupXStreamAliases(packageName);
  }

  public XmlSerializer(String packageName, AbstractDriver serializeDriver) {
    xStream = new XStream(serializeDriver);
    xStream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
    xStream.allowTypesByWildcard(new String[] { packageName + ".**" });
  }

  public void serialize(Object object, String filename) {
    try {
      xStream.toXML(object, new FileWriter(filename));
    } catch (IOException e) {
      throw new RuntimeException("Could not save data to file '" + filename + "'.");
    }
  }

  public Object deserialize(String filename) {
    try {
      return xStream.fromXML(new File(filename));
    } catch (XStreamException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Sets up the XStream aliases for model classes to make the output JSON more human-friendly.
   */
  private void setupXStreamAliases(String packageName) {
    var modelPkg = (packageName + ".model");
    var modelPkgWithSlashes = modelPkg.replace(".", File.separator);
    try (var files = Files.walk(Paths.get("."))) { // iterate over all files in project
      files.filter(Files::isRegularFile)
          // filter files to only include Java files in the the model package
          .filter(p -> p.toString().contains(modelPkgWithSlashes) && p.toString().endsWith(".java"))
          // map to filenames, then to strings, and remove ".java" suffix
          .map(Path::getFileName).map(p -> p.toString().replace(".java", ""))
          // tell XStream to set the alias for each model type name
          .forEach(modelTypeName -> {
            // need a nested try/catch here for the Class.forName() call, due to limitations of current Java compiler
            try {
              xStream.alias(modelTypeName, Class.forName(modelPkg + "." + modelTypeName));
            } catch (ClassNotFoundException e) {
              e.printStackTrace();
            }
          });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
