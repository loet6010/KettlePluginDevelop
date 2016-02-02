package org.kettlesolutions.plugin.step.helloworld;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
/**
 * 
 * @author liurh
 * @date   2016年1月28日
 * @intro  插件步骤处理类
 *
 */
public class HelloworldStep extends BaseStep implements StepInterface {

	// 构造方法
	public HelloworldStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta,
			Trans trans) {
		super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
	}
	
	// 步骤主要数据处理方法
	public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException{
		// 元数据
		HelloworldStepMeta meta = (HelloworldStepMeta) smi;
		// 数据处理
		HelloworldStepData data = (HelloworldStepData) sdi;
		
		// 获取数据
		Object[] row = getRow();
		if (row == null) {
			setOutputDone();
			return false;
		}
		
		// 第一次的处理
		if (first) {
			first = false;
			// 获取元数据
			data.outputRowMeta = getInputRowMeta().clone();
//			meta.getFields(data.outputRowMeta, getStepname(), null, null, this);
			meta.getFields(data.outputRowMeta, getStepname(), null, null, this, repository, metaStore);
		}
		
		// 设置的值
		String value = meta.getValueName();		
		// 增加列的value值设置
		Object[] outputRow = RowDataUtil.addValueData(row, getInputRowMeta().size(), value);
		// 将新的一列输出
		putRow(data.outputRowMeta, outputRow);
		
		return true;
	}

}