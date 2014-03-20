package cl.continuum.htmltopdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

/**
 * 
 * @author vutreras
 *
 */
public class HtmlToPdf {

	/**
	 * 
	 * @param inputHtml archivo con el contenido html
	 * @param inputCss archivo con el contenido css
	 * @param outputPdf archivo de salida pdf
	 * @param cleanHTML 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void convert(File inputHtml, File inputCss, OutputStream outputPdf, boolean cleanHTML) throws IOException, DocumentException {
		convert(new FileInputStream(inputHtml), new FileInputStream(inputCss), outputPdf, cleanHTML);
	}
	
	/**
	 * 
	 * @param inputHtml archivo con el contenido html
	 * @param inputCss archivo con el contenido css
	 * @param outputPdf archivo de salida pdf
	 * @param cleanHTML
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void convert(InputStream inputHtml, InputStream inputCss, OutputStream outputPdf, boolean cleanHTML) throws IOException, DocumentException {
		String css = IOUtils.toString(inputCss);
		String html = IOUtils.toString(inputHtml);
		convert(html, css, outputPdf, cleanHTML);
	}
	
	/**
	 * 
	 * @param html texto html
	 * @param css texto css
	 * @param outputPdf archivo de salida pdf
	 * @param cleanHTML
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void convert(String html, String css, OutputStream outputPdf, boolean cleanHTML) throws IOException, DocumentException {
		
		if (cleanHTML) {
			System.out.println("Limpiando html...");
			html = Jsoup.clean(html, Whitelist.relaxed());
		}

		System.out.println("Parseando html...");
		
		Document doc = Jsoup.parse(html);
		
		String base_css = "\n@media print {\n" +
				"table { page-break-after:auto; border-width: 1px; width: 100%;}\n"+
				"tr    { page-break-inside:avoid; page-break-after:auto;}\n"+
				"td    { page-break-inside:avoid; page-break-after:auto;}\n"+
				"thead { display:table-header-group; }\n"+
				"tfoot { display:table-footer-group; }\n"+
				"}\n";
		
		if (StringUtils.isNotBlank(css)) {
			css = "<style>" + base_css + css + "</style>";			
		} else {			
			css = "<style>" + base_css + "</style>";			
		}
		
		doc.head().append(css);
		
		html = StringEscapeUtils.unescapeHtml(doc.toString()).replaceAll("<h1> <br /> </h1>", "");	
		
		System.out.println("Creando pdf...");
		
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(html);
		renderer.layout();
		renderer.createPDF(outputPdf);
		
		outputPdf.close();
		
		System.out.println("Pdf creado correctamente");
	}
}
