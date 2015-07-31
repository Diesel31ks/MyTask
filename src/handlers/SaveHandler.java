package handlers;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import mytask.Perspective;
import views.TextEditor;

/**
 * Not implemented. Dialog in menu "Save" works incorrect! This handler is used
 * for saving some text to file
 * 
 * @author adm.hromov.os
 *
 */
public class SaveHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		TextEditor textEditor = (TextEditor) Perspective.getView(PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
				TextEditor.ID);
		Text textField = textEditor.getTextField();
		
		if (textField.getEditable() == false) {
			MessageDialog.openWarning(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Warning",
					"No data to save!");
			return null;
		}
		FileDialog dialog = new FileDialog(HandlerUtil.getActiveWorkbenchWindow(event).getShell());

		String absolutePathToFile = dialog.open();
		File file = new File(absolutePathToFile);

		String text = textField.getText();
		writeFile(file, text, event);

		textField.setText(textEditor.getStrings().toString());
		return null;
	}

	private void writeFile(File file, String text, ExecutionEvent event) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(text);
		} catch (IOException e) {
			MessageDialog.openError(HandlerUtil.getActiveWorkbenchWindow(event).getShell(), "Error",
					"File was not saved");
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
