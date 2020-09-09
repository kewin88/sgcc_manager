package cn.com.sgcc.inform;

import cn.com.sgcc.crypto.EncryptResult;

public interface Informer
{
	public void informError(String msg);
	public void informReady(String msg);
	public void informStart(EncryptResult result);
	public void informProgress(EncryptResult result);
	public void informEnd(EncryptResult result);
}
