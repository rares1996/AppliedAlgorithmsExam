import re


algos = ['elementaryMultiplication', 'transposedMultiplication',
         'tiledMultiplication', 'recursiveMultiplication', 'strassen']
ns = [2**i for i in range(8, 14)]


def write_latex_tabulars():
    for algo in algos:
        tabular = open(f'results/{algo}_benchmark_tabular.tex', 'w')
        tabular.write(r'\begin{table}[H]' + '\n')
        tabular.write(r'\caption{\label{tab: table} My Table}' + '\n')
        tabular.write(r'\centering' + '\n')
        tabular.write(r'\begin {tabular}{c|c}' + '\n')
        tabular.write('Matrix size $n$ & Runtime [ms]' + '\n')
        tabular.write(r'\\\hline' + '\n')
        for k in ns:
            msResult: int = open(f'results/{algo}_n={k}.txt').readline()
            tabular.write(f'{k} & {msResult}' + r'\\' + '\n')
        tabular.write(r'\end{tabular}' + '\n')
        tabular.write(r'\end{table}' + '\n')


write_latex_tabulars()