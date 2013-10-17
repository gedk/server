package cn.gedk.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.fileupload.FileItem;
/**
 * @className:FileUtils
 * @author ：gedk
 * @descripthion: 文件上传工具
 * @创建时间：2013-9-27
 */
public class FileUtils {
	public static String fileUpload(FileItem file,String rootPath,String filePath,boolean genThum) throws Exception {
		if(file == null) return "";
		rootPath = rootPath.replaceAll("\\\\", "/");
		filePath = filePath.replaceAll("\\\\", "/");
		String fullPath = rootPath+filePath;
		File dir = new File(fullPath);
		if(!dir.exists())dir.mkdirs();
		String fileName = Tools.getFileName()+"."+file.getName();
		String saveFilePath = fullPath+fileName;
		File saveFile = new File(saveFilePath);
		if(!saveFile.exists())saveFile.createNewFile();
		file.write(saveFile);
		if(genThum)genThumbnails(saveFilePath);
		return filePath+fileName;
	}
	/**
	 * 生成缩略图
	 * @param filePath
	 */
	public static void genThumbnails(String filePath) {
		if (filePath.lastIndexOf(".") > filePath.length())
			return;
		String suffix = filePath.substring(filePath.lastIndexOf("."),filePath.length());
		String sizesStr = Constant.T_SIZE;
		String[] sizes = sizesStr.split(";");
		for (String size : sizes) {
			try {
				double originalWidth = Double.parseDouble(size.split("x")[0]);
				double originalHeight = Double.parseDouble(size.split("x")[1]);
				File file = new File(filePath);
				BufferedImage image = ImageIO.read(new FileInputStream(file.getAbsolutePath()));
				if (image == null)continue;
				double width = Double.parseDouble(image.getWidth() + "");
				double height = Double.parseDouble(image.getHeight() + "");
				double xwidth = originalWidth;
				double xheight = originalHeight;
				xheight = (xwidth / width) * height;
				int rwidth = Integer.parseInt((xwidth + "").split("\\.")[0]);
				int rheight = Integer.parseInt((xheight + "").split("\\.")[0]);
				net.coobird.thumbnailator.Thumbnails.Builder<File> builder = Thumbnails.of(file);
				builder.size(rwidth, rheight).keepAspectRatio(false).toFile(new File(file.getAbsoluteFile() + size + suffix));
				/** 如果按照宽度等比例缩放缩放不到规定的尺寸就截图 **/
				if (xheight <= originalHeight) {
					Thumbnails
							.of(new File(file.getAbsoluteFile() + size + suffix))
							.sourceRegion(0, 0, rwidth, rheight)
							.size(rwidth, rheight)
							.keepAspectRatio(false)
							.toFile(new File(file.getAbsoluteFile() + size
									+ "t" + suffix));
				} else {
					Thumbnails
							.of(new File(file.getAbsoluteFile() + size + suffix))
							.sourceRegion(
									Positions.TOP_LEFT,
									Integer.parseInt((originalWidth + "")
											.split("\\.")[0]),
									Integer.parseInt((originalHeight + "")
											.split("\\.")[0]))
							.size(Integer.parseInt((originalWidth + "")
									.split("\\.")[0]),
									Integer.parseInt((originalHeight + "")
											.split("\\.")[0]))
							.keepAspectRatio(false)
							.toFile(new File(file.getAbsoluteFile() + size
									+ "t" + suffix));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
