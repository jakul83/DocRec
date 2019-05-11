

package DocumentRec;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sourceforge.tess4j.*;

public class Ocr {
	
//	static String result;
	static String enter = System.getProperty("line.separator");
	// static long start = System.currentTimeMillis();
	static int i;
	static int z;
	

	static void OCR(String inputFolder) throws IOException {
		ExecutorService e = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		System.out.println(e);
		i=0;
		z=0;
		System.out.println("=== OCR starts now on: "+inputFolder +" ===");

		File dir = new File(inputFolder + "\\przetworzone");
		dir.mkdir();
//		File dir2 = new File(inputFolder);
//		z = dir2.list().length - 1;


//			String resultDir = dir.toString();

		Files.walk(Paths.get(inputFolder)).filter(p -> p.toString().endsWith(".tiff")).forEach(p -> {

			z = z + 1;
			e.execute(new Runnable() {

				public void run() {
					String result;
					try {

						long start = System.currentTimeMillis();

						ITesseract instance = new Tesseract(); // JNA Interface Mapping
						// ITesseract instance = new Tesseract1(); // JNA Direct Mapping
						instance.setDatapath("./tessdata"); // path to tessdata directory
						File f = new File(p.toString());

						System.out.println("Start OCR on file: " + f);
						result = instance.doOCR(f);
						if (inputFolder.contains("recognizedDate")) {
							TIFFsplit.recognizedDate2 = result;
						}

						Writer SaveAfterOcr = new BufferedWriter(new OutputStreamWriter(
								new FileOutputStream(dir + "\\" + p.getFileName() + ".txt"), "UTF-8"));
						SaveAfterOcr.write(result);
						SaveAfterOcr.close();
						// System.out.println(result);

						long stop = System.currentTimeMillis();
						double t = ((stop - start) / 1000.00);
						// System.out.println("OCR execution time: dokument nr "+i+". " + t + " s");
						System.out.println("OCR on file " + p + " finished!!! Execution time: " + t + " s");
						i = i + 1;
						// Thread.currentThread().interrupt();
						// System.out.println("Executor: " + Ocr.e.isShutdown());
						System.out.println("i: " + i);
						System.out.println("z: " + z);
//						if (i==z) 	
//							{
//							
//							System.out.println("koniec i = "+i +" z = "+z);
//						
//						}
						// System.out.println(Thread.currentThread().getName() + " konczy z wynikiem " +
						// i);
					}

					catch (IOException | TesseractException e) {

						e.printStackTrace();
					}

				}

			});

			// System.out.println("ThreadEnd: " +Thread.currentThread().isInterrupted());

		});
		e.shutdown();
	
	}
	
	
	
	

}
