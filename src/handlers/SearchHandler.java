package handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import mytask.Perspective;
import mytask.SearchingDialog;
import views.TextEditor;

/**
 * This handler is used for searching/replacing some text in text field in TextEditor view
 * 
 * @author adm.hromov.os
 *
 */
public class SearchHandler implements IHandler {
	private static SearchingDialog dialog; 
	
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
					"No data to search/replace!");
			return null;
		}
		
		if (dialog==null)
			dialog = new SearchingDialog(HandlerUtil.getActiveWorkbenchWindow(event).getShell());
		dialog.open();
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
