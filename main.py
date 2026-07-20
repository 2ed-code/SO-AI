from brain.brain import Brain

brain = Brain()

print("=" * 40)
print("SO AI بدأ التشغيل")
print("اكتب (خروج) للخروج")
print("=" * 40)

while True:
    user = input("أنت: ")

    if user == "خروج":
        print("إلى اللقاء")
        break

    answer = brain.think(user)

    print("SO AI:", answer)
