
import pytest
import sof2018h

def test_exercicio15():
    assert all([k in ['sc', 'pt', 'cp', 'fr', 'pa', 'cnl'] for k in sof2018h.bn.dependencies.keys()])

    assert len(sof2018h.bn.dependencies['sc']) == 1
    assert len(sof2018h.bn.dependencies['pt']) == 1
    assert len(sof2018h.bn.dependencies['cp']) == 4
    assert len(sof2018h.bn.dependencies['fr']) == 4
    assert len(sof2018h.bn.dependencies['pa']) == 2
    assert len(sof2018h.bn.dependencies['cnl']) == 2

    assert sof2018h.bn.jointProb([(v,True) for v in sof2018h.bn.dependencies]) == 0.0001215
    
    assert sof2018h.bn.jointProb([('sc', True)]) == round(sof2018h.bn.individualProb('sc', True),5)
    assert sof2018h.bn.jointProb([('pt', False)]) == round(sof2018h.bn.individualProb('pt', False),5)

    assert round(sof2018h.bn.individualProb('pa', True),5) == 0.0163

