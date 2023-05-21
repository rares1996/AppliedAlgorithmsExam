from statistics import mean, stdev
import matplotlib.pyplot as plt

ns = [2**i for i in range(8, 14)]
algos = ['elementaryMultiplication', 'recursiveMultiplication',
         'tiledMultiplication', 'transposedMultiplication', 'strassen']

for algo in algos:
    ys = []
    for n in ns:
        y = int(open(f'results/{algo}_n={n}.txt').readline())
        ys.append(y)
    plt.plot(ns, ys, '-o')

plt.xlabel('input size $n$')
plt.ylabel('time in ms')
plt.title('Horse race on a Device A')
plt.xticks(ns)
plt.legend(algos)
plt.savefig('horse_race_DeviceA.png')
plt.show()
