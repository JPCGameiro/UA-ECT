//João Gameiro  Nºº93097
//P3-ECT-UA

package aula7.Ex2;
import java.io.*;
import java.io.RandomAccessFile;


public class Bitmap {
	
	BitmapFileHeader
	BitmapInfoHeader;
	byte[] rgbQuad;
	byte[] data;

}

class BitmapFileHeader {
	short type;				//must be 'BM' to declare a bmp-file
	int size;				//specifies the size of the file in bytes
	short reserved1; 		//must always be set to zero
	short reserved2; 		//must always be set to zero
	int offBits;			//specifies the offset from the
							//beginning of the file to the bitmap data
						


	
	public BitmapFileHeader(RandomAccessFile file) throws IOException
	{
		this.type = Short.reverseBytes(file.readShort());
		this.size = Integer.reverseBytes(file.readInt());
		this.reserved1 = Short.reverseBytes(file.readShort());
		this.reserved2 = Short.reverseBytes(file.readShort());
		this.offBits = Integer.reverseBytes(file.readInt());
	}
}

class BitmapInfoHeader{
	int size;			// the size of this header (40 bytes)
	int width;			// the bitmap width in pixels
	int height;			// the bitmap height in pixels
	short planes;		// the number of color planes being used. Must be set to 1
	short bitCount;		// the number of bits per pixel (color depth) - 1, 4, 8, 16, 24, 32
	int compression;	// the compression method being used
	int sizeImage;		// the image size. This is the size of the raw bitmap data
	int xPelsPerMeter; 	// the horizontal resolution of the image (pixel per meter)
	int yPelsPerMeter; 	// the vertical resolution of the image (pixel per meter)
	int clrUsed;		// the number of colors in the color palette,
						// or 0 to default to 2n
	int clrImportant; 	// the number of important colors used,
						// or 0 when every color is important
	
	public BitmapInfoHeader(RandomAccessFile file) throws IOException{

		this.size = Integer.reverseBytes(file.readInt());
		this.width = Integer.reverseBytes(file.readInt());
		this.height = Integer.reverseBytes(file.readInt());
		this.planes = Short.reverseBytes(file.readShort());
		this.bitCount = Short.reverseBytes(file.readShort());
		this.compression = Integer.reverseBytes(file.readInt());
		this.sizeImage = Integer.reverseBytes(file.readInt());
		this.xPelsPerMeter = Integer.reverseBytes(file.readInt());
		this.yPelsPerMeter = Integer.reverseBytes(file.readInt());
		this.clrUsed = Integer.reverseBytes(file.readInt());
		this.clrImportant = Integer.reverseBytes(file.readInt());
		
	}
}
