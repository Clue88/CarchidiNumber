import networkx as nx
import pandas as pd
from pyvis.network import Network


def save_graph(csv_file: str) -> None:
    # read the graph from a parent pointers file
    df = pd.read_csv(csv_file, header=None)
    df = df[df[1].notnull()]
    df = df.astype({1: "int64"})

    # create a NetworkX graph
    graph = nx.from_pandas_edgelist(df, source=0, target=1)

    # visualize the graph using pyvis
    net = Network(height="800px")
    net.from_nx(graph)
    net.set_edge_smooth("discrete")
    net.show("graph.html")


if __name__ == "__main__":
    save_graph("parentPointers.csv")
