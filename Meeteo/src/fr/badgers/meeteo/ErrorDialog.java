package fr.badgers.meeteo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

public class ErrorDialog {

	static final int CONNECTION = 0;
	static public Dialog createDialog(Context c, int id)
	{
		Dialog dialog = null;
		switch (id) {

		// Ici on peut définir plusieurs boites de dialogue diférentes
		case 0:
			AlertDialog.Builder builder = new AlertDialog.Builder(c);

			// On définit le message à afficher dans la boite de dialogue
			builder.setMessage("Problème lors du téléchargement des données")
					.setCancelable(false)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {

								// On définit l'action pour le oui
								public void onClick(DialogInterface dialog,
										int id) {
									// On réessaie
									dialog.cancel();
								}
							});
			dialog = builder.create();
			break;

		default:
			dialog = null;
		}
		return dialog;

	}
}
