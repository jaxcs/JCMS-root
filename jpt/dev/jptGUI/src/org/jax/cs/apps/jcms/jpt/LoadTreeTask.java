/*
 * Copyright (c) 2007, Sun Microsystems, Inc.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  * Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in 
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the name of Sun Microsystems, Inc. nor the names of its 
 *    contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */ 

package org.jax.cs.apps.jcms.jpt;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import java.util.concurrent.TimeUnit;
import javax.swing.tree.*;

/**
 * A Task that takes a MouseID and will find it's progeny  or ancestors
 * 
 * <p>
 * 
 */

public class LoadTreeTask extends Task<DefaultMutableTreeNode,Void> {
//    private static Logger logger = Logger.getLogger(LoadImageTask.class.getName());
    private final String mouseID;
    private final String function;
    private Pedigree ped;
    private DefaultMutableTreeNode myNode;
    
    public LoadTreeTask(Application app, String function, String mouseID,Pedigree myPed) {
	super(app, "LoadTreeTask"); // init title/description/messages
        ped = myPed;
        
        this.mouseID = mouseID;
        this.function = function;
    }

//    protected final URL getImageURL() { 
//	return url;
//    }

    @Override protected DefaultMutableTreeNode doInBackground() {
       
        myNode = new DefaultMutableTreeNode(this.mouseID);
	message("startedLoadingTree", "Doh");
        setMessage("Creating Mouse Tree ...");
        if (0 == "FindAncestors".compareTo(this.function)){
            ped.FindAncestors(0,this.mouseID,myNode);
            return (myNode);
        }
        else if ( 0 == "FindDescendants".compareTo(this.function)){
            ped.FindDescendants(0,this.mouseID,myNode);
            return (myNode);
        }
        else
            return(null);
    }

    private void completionMessage(String resourceKey) {
	message(resourceKey, this.mouseID, getExecutionDuration(TimeUnit.MILLISECONDS));
    }

    @Override protected void cancelled() {
    //    if (imageReader != null) {
    //.         imageReader.abort();
    //    }
	completionMessage("cancelledLoadingImage");
    }

    @Override protected void interrupted(InterruptedException e) {
//        if (imageReader != null) {
//            imageReader.abort();
//        }
	completionMessage("cancelledLoadingTree");
    }

    @Override protected void succeeded(DefaultMutableTreeNode daNode) {
	completionMessage("finishedLoadingTree");
    }

    @Override protected void failed(Throwable e) {
	completionMessage("FailedLoadingTree");
    }
    
    @Override protected void finished() {
        setMessage("");
	ped  = null;
    }

}

