/*
 *  Copyright 2012 Peter Karich info@jetsli.de
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package de.jetsli.graph.storage;

import de.jetsli.graph.util.Helper;
import java.io.File;

/**
 * @author Peter Karich, info@jetsli.de
 */
public class GraphStorageWrapper extends DefaultStorage {

    private final String folder;
    private GraphStorage tmp;

    public GraphStorageWrapper(String file, int expectedNodes, boolean mmap) {
        super(expectedNodes);
        this.folder = file;
        Directory dir;
        if (mmap)
            dir = new MMapDirectory(folder);
        else
            dir = new RAMDirectory(folder, true);
        g = tmp = new GraphStorage(dir);
    }

    @Override
    public void createNew() {
        Helper.deleteDir(new File(folder));
        tmp.createNew(osmIdToIndexMap.size());
    }

    @Override
    public boolean loadExisting() {
        return tmp.loadExisting();
    }

    @Override
    public void flush() {
        tmp.flush();
        super.flush();
    }
}
