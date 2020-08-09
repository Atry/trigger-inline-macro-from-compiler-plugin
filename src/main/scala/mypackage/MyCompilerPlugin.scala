package mypackage

import dotty.tools.dotc.ast._
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.core.Decorators._
import dotty.tools.dotc.plugins._
import dotty.tools.dotc.transform._
// import dotty.tools.dotc.transform.YCheckPositions
import dotty.tools.dotc.typer.Inliner
import dotty.tools.dotc.core.Names._
import dotty.tools.dotc.core.Constants._
import dotty.tools.dotc.core.Symbols
import dotty.tools.dotc.typer._

class MyCompilerPlugin extends StandardPlugin with PluginPhase {
  val name = getClass.getSimpleName
  val phaseName = getClass.getSimpleName

  def init(options: List[String]) = this :: Nil

  // import tpd._

  override val runsBefore = Set("posttyper")

  private def addHook(tree: tpd.Tree)(using context: Context): tpd.Tree = {
    // context.compilationUnit.needsStaging = true
    // context.definitions.AbstractFunctionClass
    // Symbols.defn.staticsMethodRef("")
    // val a = Symbols.defn.ArrayModule.moduleClass
    // ???


    import untpd._
    // context.typer.typed(
    //   untpd.Block(
    //     List(
    //       untpd.Apply(untpd.Ident(termName("println")), untpd.Literal(Constant("hooked!")))
    //     ),
    //     tree
    //   )
    // )
    object retyper extends Typer

    retyper.typed(
      Block(
        List(
          Apply(Ident(termName("println")), Literal(Constant("hooked!")))
        ),
        tree,
        // Apply(Select(Select(Ident(termName("mypackage")), termName("MyHooks")), termName("myHook")), tree)
      ),
      tree.tpe
    )

    // import tpd._
    // Apply(
    //   TypeApply(
    //     Ident(Symbols.defn.ScalaPredefModule.requiredMethodRef("identity")),
    //     TypeTree(tree.tpe.widen) :: Nil
    //   ),
    //   tree :: Nil
    // )
  }

  override def transformIf(tree: tpd.If)(using context: Context): tpd.Tree = {
    // object retyper extends Typer
    import untpd._

    context.typer.typed(Apply(Select(Select(Ident(termName("mypackage")), termName("MyHooks")), termName("ifThenElse")), List(
      tree.cond,
      tree.thenp,
      tree.elsep,
    )))

    // Apply(Select(Select(Ident(termName("mypackage")), termName("MyHooks")), termName("myHook")), tree, tree, tree)
    tree
  }
  // override def transformDefDef(tree: tpd.DefDef)(using Context): tpd.Tree = {
  //   if (tree.rhs.isEmpty) {
  //     tree
  //   } else {
  //     cpy.DefDef(tree)(rhs = addHook(tree.rhs))
  //   }
  // }
}