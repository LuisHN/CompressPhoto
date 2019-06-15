import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class compress {


    final static long startTime = System.currentTimeMillis();

    public static void main(String... args) throws IOException {

        System.out.println("Iniciei o processo de compressão");
        System.out.println(startTime);
         String path = "D:\\Fotos";
        File file = new File(path);
        File afile[] = file.listFiles();
        int i = 0;

        for (int j = afile.length; i < j; i++) {
            File arquivos = afile[i];

            File input = new File(path+"\\" +arquivos.getName());
                BufferedImage image = ImageIO.read(input);
                if(image != null)
                {
                    File output = new File("D:\\Comprimidas\\"+arquivos.getName());
                    OutputStream out = new FileOutputStream(output);
                    ImageWriter writer =  ImageIO.getImageWritersByFormatName("jpg").next();
                    ImageOutputStream ios = ImageIO.createImageOutputStream(out);
                    writer.setOutput(ios);
                    ImageWriteParam param = writer.getDefaultWriteParam();
                    if (param.canWriteCompressed()){
                        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                        param.setCompressionQuality(0.55f);
                    }
                    writer.write(null, new IIOImage(image, null, null), param);
                    out.close();
                    ios.close();
                    writer.dispose();
                }
        }
        System.out.println("Iniciei o processo de merge");
        System.out.println(System.currentTimeMillis() - startTime);

    afterCompress();
    }



    public static void afterCompress() throws IOException {
        String path = "D:\\fotos";
        File file = new File(path);
        File afile[] = file.listFiles();
        int i = 0;
        for (int j = afile.length; i < j; i++) {
            File arquivos = afile[i];

            File input = new File(path+"\\" +arquivos.getName());

            BufferedImage image = ImageIO.read(input);
            if(image != null) {

                setOverlay(image,path+"\\" +i+".png",input);
            }
        }

        System.out.println("Finalizei tudoo");
        System.out.println(System.currentTimeMillis() - startTime);
    }


    public static void setOverlay(BufferedImage image,String pathEND,File file) throws IOException {

        // load source images
        BufferedImage overlay = ImageIO.read(new File("D:\\overlaySV.png"));

        // create the new image, canvas size is the max. of both image sizes
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);

       g.drawImage(overlay, 4400, 2900, null);
        file.delete();
        // Save as new image
        ImageIO.write(combined, "png", new File(pathEND));


    }
}
