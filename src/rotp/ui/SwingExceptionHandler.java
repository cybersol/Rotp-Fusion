/*
 * Copyright 2015-2020 Ray Fowler
 * 
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     https://www.gnu.org/licenses/gpl-3.0.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rotp.ui;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import rotp.Rotp;
import rotp.ui.vipconsole.VIPConsole;

public class SwingExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        System.err.println("Error "+e.getMessage()+" in thread "+t.getName());
        e.printStackTrace();
        if (SwingUtilities.isEventDispatchThread()) {
            showError(e);
        } else {
            try {
                SwingUtilities.invokeAndWait(() -> { showError(e); });
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (InvocationTargetException ite) {
                ite.getCause().printStackTrace();
            } catch (IllegalArgumentException ite) {
                ite.getCause().printStackTrace();
            }
        }
    }
    private void showError(Throwable e) {
    	if (RotPUI.isVIPConsole) {
    		VIPConsole.throwError(e);
    	} else {
            RotPUI.instance().selectErrorPanel(e);
            Rotp.becomeVisible();   		
    	}
        if (RotPUI.useDebugFile) { // TODO BR: may be removed later
            PrintWriter debugFile = RotPUI.debugFile();
            if (debugFile != null) {
                debugFile.println(e.getMessage());
                debugFile.flush();
            }
        }
    }
}