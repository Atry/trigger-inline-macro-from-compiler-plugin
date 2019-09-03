package mypackage

import dotty.tools.dotc.ast._
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.core.Decorators._
import dotty.tools.dotc.plugins._
import dotty.tools.dotc.transform.Staging
import dotty.tools.dotc.typer.Inliner

class MyCompilerPlugin extends StandardPlugin with PluginPhase {
  val name = getClass.getSimpleName
  val phaseName = getClass.getSimpleName

  def init(options: List[String]) = this :: Nil

  import tpd._

  override val runsBefore = Set(Staging.name)

  private def addHook(tree: Tree) given Context: Tree = {
    the[Context].compilationUnit.needsStaging = true
    Inliner.inlineCall(
      Apply(
        TypeApply(
          Ident(the[Context].requiredMethodRef("mypackage.MyHooks.myHook")),
          TypeTree(tree.tpe.widen) :: Nil
        ),
        tree :: Nil
      )
    )
  }

  override def transformDefDef(tree: DefDef) given Context : Tree = {
    if (tree.rhs.isEmpty) {
      tree
    } else {
      cpy.DefDef(tree)(rhs = addHook(tree.rhs))
    }
  }
}