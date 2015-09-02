/****
Copyright (c) 2015 The Jackson Laboratory

This is free software: you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by  
the Free Software Foundation, either version 3 of the License, or  
(at your option) any later version.
 
This software is distributed in the hope that it will be useful,  
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
General Public License for more details.

You should have received a copy of the GNU General Public License 
along with this software.  If not, see <http://www.gnu.org/licenses/>.
****/

package jcms.web.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>File name:</b>  FileDownloadBean.java  <p>
 * <b>Date developed:</b>  October 2010 <p>
 *  <b>Purpose:</b>  Backing bean for file download.  <p>
 * <b>Overview:</b> Contains the getters and setters for all file elements.
 *      Reads the file content and gives open/save options to the user,
 *      also downloads the file content in the specific application
 *      depending on file format.  <p>
 * <b>Last changed by:</b>   $Author: rkavitha $ <p>
 * <b>Last changed date:</b>    <p>
 * @author Kavitha Rama
 * @version $Revision: 11533 $
 */
public class FileDownloadBean {

    private static final int DEFAULT_BUFFER_SIZE = 10240000; // 10MB

    private String inputPath = "";
    private String fileName = "";

    /**
     *  <b>Purpose:</b>  Action to download a file in specific format. <br>
     *  <b>Overview:</b> Action to download a file in specific format <br>
     *  @throws IOException  Unable to download.
     */
    public void downloadFile(String inputPath, String fileName) throws
            IOException {

        // Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        this.inputPath = inputPath;
        this.fileName = fileName;

        // get the instance of file to read already created file
        File file = new File(this.inputPath);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        file.deleteOnExit();

        try {
            // Open file.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

            // Init servlet response.
            response.reset();
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + this.fileName + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalize task.
            output.flush();
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }

        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();
    }

    /*
     * Close the IO stream
     */
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it. It may be useful to
                // know that this will generally only be thrown when the client aborted the download.
                e.printStackTrace();
            }
        }
    }

    /**
     * @return the inputPath
     */
    public String getInputPath() {
        return inputPath;
    }

    /**
     * @param inputPath the inputPath to set
     */
    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}