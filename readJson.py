import requests,json
import csv

word_list=[]
output_list=[]

# 对前word_number_limit个高频单词进行知识提取，最大不超过333332
word_number_limit=15500

# 每个概念获取边的条数=page_limit*20，至少为1
page_limit=3

def read_words():
    with open('words.csv', 'r') as csvfile:
        reader = csv.reader(csvfile)
        word_list = [row[0] for row in reader]
        # print(len(word_list))
        return word_list

def build_into_sentence(obj):
    print("请求成功，导入20条边处理...")
    i=0
    temp_list=[]
    while i<len(obj['edges']):
        surfaceText = obj['edges'][i]['surfaceText']
        if surfaceText != None:
            start_language = obj['edges'][i]['start']['language']
            end_language = obj['edges'][i]['end']['language']
            if start_language == 'en'  and end_language == 'en':
                output = surfaceText.replace('[','').replace(']','')
                output_list.append(output)
                temp_list.append(output)
        i+=1
    print('20条边处理完毕，生成语句：'+str(temp_list))
    return temp_list



def handle_concept(str):
    count=0
    nextPage='/c/en/'+str

    while True:
        print('请求uri:'+nextPage+'...')
        obj = requests.get('http://api.conceptnet.io/'+nextPage).json()
        hasView = 'view' in obj
        if hasView:
            sentences = build_into_sentence(obj)
            write_into_txt(sentences)
            hasNextPage = 'nextPage' in obj['view']
            if hasNextPage and count<page_limit:
                nextPage = obj['view']['nextPage']
                count+=1
            else:
                break
        else:
            break



def write_into_txt(list):
    list = sorted(set(list), key=list.index)
    file_handle = open('1.txt', mode='a')

    print(len(list))
    i=0

    while i<len(list):
        str = list[i]
        file_handle.write(str+'\n')
        i+=1

    file_handle.close()


word_list=read_words()
count=1
while count<word_number_limit+1:
    print('开始处理第'+str(count)+'个概念:'+word_list[count-1]+'+++++++++++++++')
    handle_concept(word_list[count-1])
    print('第'+str(count)+'个概念处理完毕')
    count+=1


print('脚本执行完毕')