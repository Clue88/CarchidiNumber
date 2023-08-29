# CarchidiNumber

Carchidi Count: exploring the interconnectedness of Penn's professor network

A wise woman (Ms. Amy Calhoun) once spoke of the infamous Michael A. Carchidi, his love for teaching, and his undeniable
versatility. Professor Carchidi has taught 26 different courses across 11 different departments here at Penn
(EAS, ENGR, ENM, ESE, MATH, MEAM, OIDD, OPIM, PHYS, STAT, SYS)! Inspired by scientists' "collaboration distance" Erdős
numbers, and by actors' Bacon Numbers, this project calculates Penn Professors' Carchidi Counts.

A professor's Carchidi Count is their degree of separation from Prof. Carchidi, where there exists an edge between two
professors if they have taught the same class. This project combines graph algorithms with internet data acquisition to
retrieve professor/course information from Penn Course Review, and subsequently uses BFS to find the shortest path
between any two given professors via shared classes taught (if such paths exist).

![The Carchidi tree graph starting from Prof. Carchidi (up to ~8000 nodes)](https://github.com/Clue88/CarchidiNumber/blob/main/carchidi_tree.png)
The Carchidi tree graph starting from Prof. Carchidi (up to ~8000 nodes)
