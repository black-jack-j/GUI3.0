package gui;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
public class XMLParser{
	public static Keeper getStorage(File f) throws JDOMException, IOException {
		SAXBuilder sab = new SAXBuilder();
		Keeper tmp = null;
		try{
			Document doc = sab.build(f);
			Element rootElem = doc.getRootElement();
			tmp = new Keeper(rootElem.getAttributeValue("name"));
			List<Element> collectionElements = rootElem.getChildren("object");
			for (Element el:collectionElements){
				Territory place = new Territory(el.getChild("name").getValue(), Double.parseDouble(el.getChild("square").getValue()));
				tmp.addComponent(el.getAttributeValue("key"), place);
			}
		}catch(Exception e){
			tmp = new Keeper("Default");
		}
		return tmp;
	}
}
