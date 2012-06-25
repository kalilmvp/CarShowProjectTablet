package br.com.google.android.fragments;

import roboguice.fragment.RoboFragment;
import br.com.google.android.R;
import br.com.google.android.utils.ConexaoUtil;
import br.com.google.android.utils.Transacao;
import br.com.google.android.utils.TransacaoFragmentTask;
import br.com.google.android.utils.Utils;

public class BaseFragment extends RoboFragment {
	private int progressId = R.id.progressBar;
	
	protected void alert(int mensagem) {
		Utils.createAlertDialog(getActivity(), mensagem);
	}
	
	public void startTransacao(Transacao transacao) {
		boolean redeOk = ConexaoUtil.isNetworkAvailable(getActivity());
		if (redeOk) {
			TransacaoFragmentTask task = new TransacaoFragmentTask(this, transacao, this.progressId);
			task.execute();
		} else {
			Utils.createAlertDialog(getActivity(), R.string.erro_conexao_indisponivel);
		}
	}
	
	public void setProgressId(int progressId) {
		this.progressId = progressId; 
	} 
}