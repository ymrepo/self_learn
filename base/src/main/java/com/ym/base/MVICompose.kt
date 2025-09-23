package com.ym.base

fun <T> compose(vararg functions: (T) -> T): (T) -> T =
    { x -> functions.foldRight(x) { f, composed -> f(composed) } }

fun <T> compose(functions: List<(T) -> T>): (T) -> T =
    { x -> functions.foldRight(initial = x) { t, acc -> t(acc) } }