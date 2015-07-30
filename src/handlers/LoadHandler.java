package handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import mytask.Perspective;
import views.TextEditor;

/**
 * This handler is used for loading some text from file
 * 
 * @author adm.hromov.os
 *
 */
public class LoadHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FileDialog dialog = new FileDialog(HandlerUtil.getActiveWorkbenchWindow(event).getShell());
		String absolutePathToFile = dialog.open();
		File file = new File(absolutePathToFile);

		TextEditor textEditor = (TextEditor) Perspective.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
				TextEditor.ID);
		textEditor.getTextField().setEditable(true);
		readFile(file, textEditor.getStrings(), event);

		textEditor.getTextField().setText(textEditor.getStrings().toString());
		return null;
	}

	private void readFile(File file, List<String> strings, ExecutionEvent event) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				strings.add(line+"\n");
			}
		} catch (FileNotFoundException e) {
			MessageDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Error", "File not found");
		} catch (IOException e1) {
			MessageDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Error",
					"Error of input/output was happened");
		}
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}
}
