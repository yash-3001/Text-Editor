package texteditor;



public class Function_Edit {
	TextEditor txt;
	public Function_Edit(TextEditor txt) {
		this.txt = txt;
	}
	public void undo() {
		txt.um.undo();
	}
	public void redo() {
	 txt.um.redo();
	}
}

