package cl.continuum.htmltopdf;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;

/**
 * 
 * @author vutreras
 *
 */
@SuppressWarnings("static-access")
public class MainHtmlToPdf {

	public static void main(String[] args) throws Exception {
		
		Options options = new Options();

		Option htmlFile = OptionBuilder.withArgName("file")
                .hasArg()
                .withDescription("Ruta al archivo HTML")
                .create("htmlfile");
		
		Option htmlText = OptionBuilder.withArgName("text")
                .hasArg()
                .withDescription("Texto HTML")
                .create("html");
		
		Option cssFile = OptionBuilder.withArgName("file")
                .hasArg()
                .withDescription("Ruta al archivo CSS")
                .create("cssfile");
		
		Option cssText = OptionBuilder.withArgName("text")
                .hasArg()
                .withDescription("Texto CSS")
                .create("css");
		
		Option pdfFile = OptionBuilder.withArgName("file")
                .hasArg()
                .withDescription("Ruta al archivo PDF generado")
                .isRequired(true)
                .create("pdffile");
		
		Option debugHtml = OptionBuilder
                .withDescription("Genera un archivo html_debug.html")
                .create("debughtml");

		options.addOption(htmlFile);
		options.addOption(htmlText);
		options.addOption(cssFile);
		options.addOption(cssText);
		options.addOption(pdfFile);
		options.addOption(debugHtml);
		
		if (args == null || args.length <= 1) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "html-to-pdf", options );
		} else {
		
			try {
				CommandLineParser parser = new BasicParser();
				CommandLine cmd = parser.parse( options, args);
				
				String html = null;
				String css = null;
				String pdffile = null;
				
				if (cmd.hasOption("htmlfile")) {
					String htmlfile = cmd.getOptionValue("htmlfile");
					html = IOUtils.toString(new FileInputStream(htmlfile));
				} else if (cmd.hasOption("html")) {
					html = cmd.getOptionValue("html");
				}
				
				if (cmd.hasOption("cssfile")) {
					String cssfile = cmd.getOptionValue("cssfile");
					css = IOUtils.toString(new FileInputStream(cssfile));
				} else if (cmd.hasOption("css")) {
					css = cmd.getOptionValue("css");
				}
				
				pdffile = cmd.getOptionValue("pdffile");
				
				long tiempoInicio = System.currentTimeMillis();
				
				boolean debughtml = cmd.hasOption("debughtml");
				
				OutputStream outputPdf = new FileOutputStream(pdffile);
				HtmlToPdf.convert(html, css, outputPdf, true, debughtml);
				
				long totalTiempo = System.currentTimeMillis() - tiempoInicio;
				
				System.out.println("Tiempo total de la ejecucion: " + (totalTiempo/1000) + " seg");
				
		    } catch(ParseException exp) {
		        System.err.println( "Error en los parametros: " + exp.getMessage());
		    }
		}
	}

}
