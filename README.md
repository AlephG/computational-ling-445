# Coursework for Computational Linguistics
This repository contains assignment submissions for Computational Linguistics taught by Prof. Timothy O'Donnell at McGill University in the Fall 2021 semester.
Files for each assignment are in their respective folders ps[x].

## ps1
In this problem set, we explored fundamental ideas from functional programming using the LISP dialect Clojure.
## ps2
In this problem set, we learn more about recursive procedures which are crucial in a functional programming setting. Furthermore, we prove certain properties of formal languages using set theory notions (see pdf from that problem set)
## ps3
In this problem set, I manipulated a toy linguistic corpus taken from Moby Dick. I implement my first probabilistic model of language: a uniform bag-of-words language. In this simplistic model, every word or sentence is generated independently and with uniform probability. I define both a probabilistic generative model (e.g. a sampler) and a probabilistic recognizer (e.g. a scorer) for the uniform BOW language.
## ps4
In this problem set, I consider a a variant of a hierarchical bag-of-words (BOW) model. In this type of model, the prior distribution is often defined using a Dirichlet distribution over the parameter vector θ of the BOW model. The Dirichlet distribution is a continuous distribution over the simplex - it assigns probability density to all the uncountably many points on the simplex. For this problem set, I consider a much simpler prior distribution over the parameters θ. The distribution used is discrete, and in particular it only assigns positive probability to a finite number of values of θ. In particular, I also explore foundational probability theory tools such as computing marginal, conditional and joint distributions in the context of langauge modelling.
## ps5
In this problem set, explore n-gram models of language which capture word dependencies by deriving various equations, proving/disproving k-locality for certain classes of languages and proving that k-local languages are closed under intersection. See [this document](https://github.com/AlephG/computational-ling-445/blob/main/ps5/ps5-legris-solim.pdf) for more mathematical details.
