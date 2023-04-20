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
def pearson_similarity(a, b):
    return np.dot((a - np.mean(a)), (b - np.mean(b))) / ((np.linalg.norm(a - np.mean(a))) * (np.linalg.norm(b - np.mean(b))))

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


app = Flask(__name__)
@app.route('/similar', methods=['GET', 'POST'])
def file_upload():
    if request.method == 'GET':
        target_index = int(request.args.get('index'))
        pearson_result = []
        target_numpy = group_train_data.iloc[target_index].to_numpy()[1:]

        for index, row in group_train_data.iterrows():
            if index == target_index:
                continue
            index_numpy = row.to_numpy()[1:]
            pearson_result.append((index, pearson_similarity(target_numpy, index_numpy)))

        pearson_result = sorted(pearson_result, key=lambda x: x[1], reverse=True)
        json_result = []

        for idx in range(10):
            result_data = group_train_data.loc[pearson_result[idx][0]].to_dict()
            name_list = result_data["COFFEE_BEAN"].split(' ')
            result_data["growing_region"] = name_list[0]    # [7:]
            result_data["tree_variety"] = name_list[1]  # [13:]
            result_data["roast_level"] = name_list[2]
            result_data["index"] = pearson_result[idx][0]
            result_data["agtron"] = str(result_data["BEFORE_AGTRON"]) + "/" + str(result_data["AFTER_AGTRON"])
            del result_data['COFFEE_BEAN']
            del result_data['BEFORE_AGTRON']
            del result_data['AFTER_AGTRON']
            result_data = dict_key_lower(result_data)
            json_result.append(result_data)

        return json_result, 200
    else:
        req = request.get_json()
        req_growing_region = req["growing_region"]
        req_tree_varierty = req["tree_variety"]
        req_roast_level = req["roast_level"]

        target_coffee_bean = req_growing_region + " " + req_tree_varierty + " " + req_roast_level
        target_row = group_train_data.index[group_train_data["COFFEE_BEAN"] == target_coffee_bean]
        if not target_row.empty:
            target_index = int(target_row[0])
        else:
            target_index = 1
        print("target index: " + str(target_index))
        pearson_result = []
        target_numpy = group_train_data.iloc[target_index].to_numpy()[1:]

        for index, row in group_train_data.iterrows():
            if index == target_index:
                continue
            index_numpy = row.to_numpy()[1:]
            pearson_result.append((index, pearson_similarity(target_numpy, index_numpy)))

        pearson_result = sorted(pearson_result, key=lambda x: x[1], reverse=True)
        json_result = []

        for idx in range(10):
            result_data = group_train_data.loc[pearson_result[idx][0]].to_dict()
            name_list = result_data["COFFEE_BEAN"].split(' ')
            result_data["growing_region"] = name_list[0]  # [7:]
            result_data["tree_variety"] = name_list[1]  # [13:]
            result_data["roast_level"] = name_list[2]
            result_data["index"] = pearson_result[idx][0]
            result_data["acidity"] = result_data.pop("ACIDITY/STRUCTURE")
            result_data["agtron"] = str(result_data["BEFORE_AGTRON"]) + "/" + str(result_data["AFTER_AGTRON"])
            del result_data['COFFEE_BEAN']
            del result_data['BEFORE_AGTRON']
            del result_data['AFTER_AGTRON']
            result_data = dict_key_lower(result_data)
            json_result.append(result_data)

        return json_result, 200


if __name__ == '__main__':
    app.run(port=80)