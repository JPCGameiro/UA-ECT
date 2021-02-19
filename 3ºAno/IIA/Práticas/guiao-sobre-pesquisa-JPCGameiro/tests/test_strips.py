import pytest
from blocksworld import Floor, Holds, On, Free, a, b, c, d, e, Stack, Putdown, HandFree
from strips import STRIPS
from tree_search import SearchProblem, SearchTree

@pytest.fixture
def initial_state():
    return [ Floor(a), Floor(b), Floor(d), Holds(e), On(c,d), Free(a), Free(b), Free(c) ]

@pytest.fixture
def goal_state():
    return [ Floor(c), On(d,c), On(e,d), On(a,e), Floor(b) ]

def test_exercicio1(initial_state):
    bwdomain = STRIPS()

    actions = bwdomain.actions(initial_state)

    assert all(op in str(actions) for op in ["Stack(e,b)", "Stack(e,a)", "Stack(e,c)", "Putdown(e)"])

    assert bwdomain.result(initial_state, actions[-1]) == {Free(e), On(c,d), Floor(d), Floor(b), HandFree(), Floor(a), Free(a), Free(c), Free(b), Floor(e)}

    assert bwdomain.satisfies(initial_state, [On(c,d), Free(a)])

def test_exercicio2(initial_state, goal_state):
    bwdomain = STRIPS()

    p = SearchProblem(bwdomain,initial_state,goal_state)

    t = SearchTree(p)

    t.search()

    assert str(t.plan) == "[Stack(e,b), Unstack(c,d), Putdown(c), Pickup(d), Stack(d,c), Unstack(e,b), Stack(e,d), Pickup(a), Stack(a,e)]"