package handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.PlatformUI;

import mytask.Perspective;
import views.TextEditor;

/**
 * This handler is used for creating new text in text field in TextEditor's view
 * 
 * @author adm.hromov.os
 *
 */

public class CreateNewHandler implements IHandler {

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
		textEditor.getTextField().setEditable(true);
		textEditor.getTextField().setText("");
		return null;
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
