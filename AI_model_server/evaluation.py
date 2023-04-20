# Library Declaration
import sys
import os
import pandas as pd
import numpy as np
from operator import itemgetter
from flask import Flask, render_template, request, redirect, url_for
# from flaskext.mysql import MySQL
from werkzeug.utils import secure_filename

# 유사도 함수 선언
def similarity_fun(a, b):
    return cosine_similarity(a, b)


# 유클리드 거리를 이용한 유사도 함수
def euclidean_similarity(a, b):
    return np.sqrt(np.sum((a-b)**2))


# 피어슨 유사도 함수 선언
def pearson_similarity(a, b):
    return np.dot((a - np.mean(a)), (b - np.mean(b))) / ((np.linalg.norm(a - np.mean(a))) * (np.linalg.norm(b - np.mean(b))))


# 코사인 유사도 함수 선언
def cosine_similarity(a, b):
    return  np.dot(a, b)/(np.linalg.norm(a)*np.linalg.norm(b))


# 딕셔너리 key lower 함수
def dict_key_lower(data):
    if isinstance(data, dict):
        return {k.lower():dict_key_lower(v) for k,v in data.items()}
    elif isinstance(data, list):
        return [dict_key_lower(v) for v in data]
    else:
        return data

# 데이터 가져오기 + 전처리
train_data = pd.read_excel(".\dataset.xlsx", engine="openpyxl")
train_data = train_data.dropna(subset=["ROAST_LEVEL"])
train_data = train_data.dropna(subset=["AGTRON"])
train_data.rename(columns={'AGTRON': "BEFORE_AGTRON"}, inplace=True)
train_data["BEFORE_AGTRON"] = train_data["BEFORE_AGTRON"].str.split('/')
train_data.insert(4, "AFTER_AGTRON", train_data["BEFORE_AGTRON"].apply(itemgetter(1)).astype(int))
train_data["BEFORE_AGTRON"] = train_data["BEFORE_AGTRON"].apply(itemgetter(0)).astype(int)
grouped_train_data = train_data.groupby(['GROWING_REGION', 'TREE_VARIETY', 'ROAST_LEVEL']).mean().fillna(5.0)
group_train_data = grouped_train_data.reset_index()
group_train_data["COFFEE_BEAN"] = group_train_data["GROWING_REGION"] + " " + group_train_data["TREE_VARIETY"] + \
                                  " " + group_train_data["ROAST_LEVEL"]

# Column Relocation
col1 = group_train_data.columns[-1:].to_list()
col2 = group_train_data.columns[:-1].to_list()
new_col = col1 + col2
group_train_data = group_train_data[new_col]
group_train_data = group_train_data.drop(["GROWING_REGION", "TREE_VARIETY", "ROAST_LEVEL"], axis=1)
group_train_data["AROMA"] = group_train_data["AROMA"] * 10.0
group_train_data["ACIDITY/STRUCTURE"] = group_train_data["ACIDITY/STRUCTURE"] * 10.0
group_train_data["BODY"] = group_train_data["BODY"] * 10.0
group_train_data["FLAVOR"] = group_train_data["FLAVOR"] * 10.0
group_train_data["AFTERTASTE"] = group_train_data["AFTERTASTE"] * 10.0

target_index = 1
pearson_result = []
cosine_result = []
euclidean_result = []
target_numpy = group_train_data.iloc[target_index].to_numpy()[1:]

for index, row in group_train_data.iterrows():
    if index == target_index:
        continue
    index_numpy = row.to_numpy()[1:]
    pearson_result.append((index, pearson_similarity(target_numpy, index_numpy)))
    cosine_result.append((index, cosine_similarity(target_numpy, index_numpy)))
    euclidean_result.append((index, euclidean_similarity(target_numpy, index_numpy)))

pearson_result = sorted(pearson_result, key=lambda x: x[1], reverse=True)
cosine_result = sorted(cosine_result, key=lambda x: x[1], reverse=True)
euclidean_result = sorted(euclidean_result, key=lambda x: x[1], reverse=True)

print(pearson_result)
print()
print(cosine_result)
print()
print(euclidean_result)


