import re

algos = ['transposeRec']


def write_latex_tabulars():
    for algo in algos:
        tabular = open(f'best_s/{algo}_best_s_tabular.tex', 'w')
        tabular.write(r'\begin{table}[H]' + '\n')
        tabular.write(r'\caption{\label{tab: table} My Table}' + '\n')
        tabular.write(r'\centering' + '\n')
        tabular.write(r'\begin {tabular}{c|c|c}' + '\n')
        tabular.write('Tile size $s$ & Number of tiles & avg. time' + '\n')
        tabular.write(r'\\\hline' + '\n')
        Lines = open(f'best_s/bestSfor_{algo}.txt').readlines()
        for n in Lines[:-2]:
            results = n.split(" ")
            real_results: list[int] = []
            for info in results:
                res = re.sub('[^0-9]', '', info)
                if len(res) > 0:
                    real_results.append(res)
            if len(real_results) > 0:
                tabular.write(
                    f'{real_results[0]} & {real_results[1]} & {real_results[2]}' + r'\\' + '\n')
        tabular.write(r'\end{tabular}' + '\n')
        tabular.write(r'\end{table}' + '\n')


write_latex_tabulars()
