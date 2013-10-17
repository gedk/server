package cn.gedk.action;

import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import cn.gedk.core.Action;
import cn.gedk.core.Context;
import cn.gedk.core.Result;
import cn.gedk.utils.FileUtils;
import cn.gedk.utils.Tools;

public class FileUploadAction extends Action {
	
	private String rootPath;
	private String imagePath;
	public FileUploadAction(Context context) {
		super(context);
		rootPath = Tools.getValueFromMapByDefaultValue(context.getTextFields(), "projectPath","");
		imagePath = "images/uploadImages/"+Tools.getDateStr()+"/";
	}
	/**
	 * @author Administrator
	 * @category 1
	 * @exception 3
	 * @see 4
	 * @since 5
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Result doUpload(Context context) throws Exception{
		for(Map.Entry<String, FileItem> item:context.getFileFields().entrySet()){
			FileUtils.fileUpload(item.getValue(),rootPath, imagePath, false);
		}
		return null;
	}
}
