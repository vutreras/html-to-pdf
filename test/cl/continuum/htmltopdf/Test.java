package cl.continuum.htmltopdf;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author vutreras
 *
 */
public class Test {

	public static void main(String[] args) {
		
		//Prueba sin parametros
		try {
			String[] args_ = args;
			System.out.println("----------------------------------");
			System.out.println("Prueba 1: " + Arrays.asList(args_));
			MainHtmlToPdf.main(args_);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Prueba usando -htmlfile y -cssfile
		try {
			File fileHtml = new File("test/documento_prueba.html");
			File fileCss = new File("test/test.css");
			File filePdf = new File("out1.pdf");
			String[] args_ = {"-htmlfile", fileHtml.getAbsolutePath(), "-cssfile", fileCss.getAbsolutePath(), "-pdffile", filePdf.getAbsolutePath()};
			System.out.println("----------------------------------");
			System.out.println("Prueba 2: " + Arrays.asList(args_));
			MainHtmlToPdf.main(args_);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Prueba usando -html y -css
		try {
			File fileHtml = new File("test/documento_prueba.html");
			File filePdf = new File("out2.pdf");
			String html = IOUtils.toString(new FileInputStream(fileHtml));
			String css = "td { border-width: 1px; background-color: #ccc; border-color: #000; border-style: solid; }\n"+
			"h1 {font-size: 1.2em;}\n"+
			"h2 {font-size: 1em;}\n";			
			String[] args_ = {"-html", html, "-css", css, "-pdffile", filePdf.getAbsolutePath()};
			System.out.println("----------------------------------");
			System.out.println("Prueba 3: " + Arrays.asList(args_).toString().replaceAll("\n", ""));
			MainHtmlToPdf.main(args_);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
